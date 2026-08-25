package com.example.smart_wallet.modules.expense.infrastructure.parser;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.application.port.out.NfcExpenseParser;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcItem;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class NfcExpenseParserJsoup implements NfcExpenseParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public CreateExpenseCommand parseExpense(String html) {
        Document doc = Jsoup.parse(html);

        String storeName = extractStoreName(doc);
        BigDecimal totalPaid = extractTotalPaid(doc);
        String paymentType = extractPaymentType(doc);
        LocalDateTime purchaseDate = extractPurchaseDate(doc);
        Integer installments = extractInstallments(paymentType);

        String paymentMethod;

        if (installments == null || installments < 2) {
            paymentMethod = "payInFull";
        } else {
            paymentMethod = "installment";
        }

        return new CreateExpenseCommand(
                storeName,
                totalPaid,
                paymentType,
                paymentMethod,
                null,
                purchaseDate,
                installments,
                "pending",
                null,
                null
        );
    }

    @Override
    public ExpenseNfcMetadata parseItems(String html) {
        Document doc = Jsoup.parse(html);

        ExpenseNfcMetadata expenseNfcMetadata = ExpenseNfcMetadata.builder()
                .rawHtml(html)
                .accessKey(extractAccessKey(html))
                .issuedAt(extractPurchaseDate(html))
                .build();

        List<ExpenseNfcItem> expenseNfcItems = new ArrayList<>();

        Elements rows = doc.select("#myTable tr");

        for (Element row : rows) {
            Elements cols = row.select("td");

            if (cols.size() < 4) {
                continue;
            }

            String description = cols.get(0)
                    .select("h7")
                    .text()
                    .trim();

            String quantityText = cols.get(1)
                    .text()
                    .replace("Qtde total de ítens:", "")
                    .trim();

            String totalText = cols.get(3)
                    .text()
                    .replace("Valor total R$:", "")
                    .replace("R$", "")
                    .trim();

            String unitText = cols.get(2).text().trim();
            String unit = unitText.replace("UN:", "").trim();

            ExpenseNfcItem expenseNfcItem = ExpenseNfcItem.builder()
                    .description(description)
                    .quantity(parseDecimal(quantityText))
                    .totalPrice(parseMoney(totalText))
                    .expenseNfcMetadata(expenseNfcMetadata)
                    .unit(unit)
                    .build();

            expenseNfcItems.add(expenseNfcItem);
        }

        expenseNfcMetadata.setExpenseNfcItems(expenseNfcItems);

        return expenseNfcMetadata;
    }

    private String extractAccessKey(String html) {
        Document doc = Jsoup.parse(html);

        Element collapse = doc.selectFirst("#collapseTwo td");

        if (collapse == null) {
            return null;
        }

        return collapse.text()
                .replace(".", "")
                .replace("/", "")
                .replace("-", "")
                .trim();
    }

    private LocalDateTime extractPurchaseDate(String html) {
        Document doc = Jsoup.parse(html);

        Elements rows = doc.select("#collapse4 table");

        for (Element table : rows) {
            if (table.text().contains("Data Emissão")) {
                Element dateCell = table.select("tbody tr td").last();

                if (dateCell != null) {
                    return LocalDateTime.parse(dateCell.text().trim(), DATE_FORMATTER);
                }
            }
        }

        return null;
    }

    private Integer extractInstallments(String paymentType) {
        if (Objects.equals(paymentType, "debit") || Objects.equals(paymentType, "money")) {
            return null;
        }

        return 1;
    }

    private LocalDateTime extractPurchaseDate(Document doc) {
        Elements rows = doc.select("table.table-hover");

        for (Element table : rows) {
            String text = table.text();

            if (text.contains("Data Emissão")) {
                Elements tds = table.select("tbody tr td");

                if (tds.size() >= 4) {
                    String date = tds.get(3).text().trim();

                    return LocalDateTime.parse(date, DATE_FORMATTER);
                }
            }
        }

        return LocalDateTime.now();
    }

    private String extractPaymentType(Document doc) {
        String paymentText = doc.select("div[id*=j_idt82]")
                .text()
                .toLowerCase();

        if (paymentText.contains("crédito")) {
            return "credit";
        }

        if (paymentText.contains("débito")) {
            return "debit";
        }

        return "money";
    }

    private BigDecimal extractTotalPaid(Document doc) {
        Elements rows = doc.select("div.row");

        for (Element row : rows) {
            Element strong = row.select("strong").first();

            if (strong == null) {
                continue;
            }

            String label = strong.text().trim();

            if (label.contains("Valor pago")) {
                Elements strongs = row.select("strong");

                if (strongs.size() > 1) {
                    String value = strongs.get(1).text().trim();

                    return parseMoney(value);
                }
            }
        }

        return BigDecimal.ZERO;
    }

    private String extractStoreName(Document doc) {
        return doc.select("thead h4 b").text().trim();
    }

    private BigDecimal parseMoney(String value) {
        String normalized = value.replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();

        if (normalized.isBlank()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(normalized);
    }

    private BigDecimal parseDecimal(String value) {
        String normalized = value.replace(",", ".")
                .replaceAll("[^0-9.]", "");

        if (normalized.isBlank()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(normalized);
    }
}
