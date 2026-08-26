package com.example.smart_wallet.modules.expense.infrastructure.parser;

import com.example.smart_wallet.modules.expense.application.dto.CreateExpenseCommand;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcItem;
import com.example.smart_wallet.modules.expense.domain.entity.ExpenseNfcMetadata;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NfcExpenseParserJsoupTest {

    private final NfcExpenseParserJsoup parser = new NfcExpenseParserJsoup();

    private String htmlWithPaymentTypeAndTotal(String paymentTypeLabel) {
        return """
                <html>
                <body>
                <table><thead><tr><td><h4><b> Mercado Central </b></h4></td></tr></thead></table>
                <div class="row"><strong>Valor pago:</strong><strong>R$ 1.234,56</strong></div>
                <div id="j_idt82_label">%s</div>
                <table class="table-hover">
                <tbody>
                <tr><td>Data Emissão</td><td>Num</td><td>Serie</td><td>21/03/2024 10:15:00</td></tr>
                </tbody>
                </table>
                </body>
                </html>
                """.formatted(paymentTypeLabel);
    }

    @Test
    void parsesACreditExpenseAsPayInFull() {
        CreateExpenseCommand command = parser.parseExpense(htmlWithPaymentTypeAndTotal("Forma de pagamento: Cartão de Crédito"));

        assertThat(command.description()).isEqualTo("Mercado Central");
        assertThat(command.cost()).isEqualByComparingTo("1234.56");
        assertThat(command.paymentType()).isEqualTo("credit");
        assertThat(command.paymentMethod()).isEqualTo("payInFull");
        assertThat(command.purchaseDate()).isEqualTo(LocalDateTime.of(2024, 3, 21, 10, 15, 0));
        assertThat(command.installments()).isEqualTo(1);
        assertThat(command.status()).isEqualTo("pending");
        assertThat(command.walletId()).isNull();
        assertThat(command.cardId()).isNull();
    }

    @Test
    void parsesADebitExpenseWithNoInstallments() {
        CreateExpenseCommand command = parser.parseExpense(htmlWithPaymentTypeAndTotal("Forma de pagamento: Cartão de Débito"));

        assertThat(command.paymentType()).isEqualTo("debit");
        assertThat(command.installments()).isNull();
        assertThat(command.paymentMethod()).isEqualTo("payInFull");
    }

    @Test
    void defaultsToMoneyPaymentTypeWhenNoCardIsMentioned() {
        CreateExpenseCommand command = parser.parseExpense(htmlWithPaymentTypeAndTotal("Dinheiro"));

        assertThat(command.paymentType()).isEqualTo("money");
        assertThat(command.installments()).isNull();
    }

    @Test
    void fallsBackToZeroWhenTotalPaidIsNotFound() {
        String html = """
                <html>
                <body>
                <table><thead><tr><td><h4><b> Loja </b></h4></td></tr></thead></table>
                <div id="j_idt82_label">Dinheiro</div>
                </body>
                </html>
                """;

        CreateExpenseCommand command = parser.parseExpense(html);

        assertThat(command.cost()).isEqualByComparingTo("0");
    }

    @Test
    void fallsBackToNowWhenPurchaseDateIsNotFound() {
        String html = """
                <html>
                <body>
                <table><thead><tr><td><h4><b> Loja </b></h4></td></tr></thead></table>
                <div id="j_idt82_label">Dinheiro</div>
                </body>
                </html>
                """;

        LocalDateTime before = LocalDateTime.now();
        CreateExpenseCommand command = parser.parseExpense(html);
        LocalDateTime after = LocalDateTime.now();

        assertThat(command.purchaseDate()).isBetween(before, after);
    }

    @Test
    void parsesItemsFromTheItemsTable() {
        String html = """
                <html>
                <body>
                <div id="collapseTwo"><table><tr><td>1234.5678/9012-3456</td></tr></table></div>
                <div id="collapse4">
                <table>
                <tbody>
                <tr><td>Data Emissão</td><td>21/03/2024 10:15:00</td></tr>
                </tbody>
                </table>
                </div>
                <table id="myTable">
                <tr>
                <td><h7>Arroz</h7></td>
                <td>Qtde total de ítens:2</td>
                <td>UN:KG</td>
                <td>Valor total R$:10,50</td>
                </tr>
                <tr><td>linha incompleta</td></tr>
                </table>
                </body>
                </html>
                """;

        ExpenseNfcMetadata metadata = parser.parseItems(html);

        assertThat(metadata.getRawHtml()).isEqualTo(html);
        assertThat(metadata.getAccessKey()).isEqualTo("1234567890123456");
        assertThat(metadata.getIssuedAt()).isEqualTo(LocalDateTime.of(2024, 3, 21, 10, 15, 0));
        assertThat(metadata.getExpenseNfcItems()).hasSize(1);

        ExpenseNfcItem item = metadata.getExpenseNfcItems().get(0);
        assertThat(item.getDescription()).isEqualTo("Arroz");
        assertThat(item.getQuantity()).isEqualByComparingTo("2");
        assertThat(item.getUnit()).isEqualTo("KG");
        assertThat(item.getTotalPrice()).isEqualByComparingTo("10.50");
        assertThat(item.getExpenseNfcMetadata()).isEqualTo(metadata);
    }

    @Test
    void returnsNullAccessKeyAndIssuedAtWhenSectionsAreMissing() {
        String html = "<html><body><table id=\"myTable\"></table></body></html>";

        ExpenseNfcMetadata metadata = parser.parseItems(html);

        assertThat(metadata.getAccessKey()).isNull();
        assertThat(metadata.getIssuedAt()).isNull();
        assertThat(metadata.getExpenseNfcItems()).isEmpty();
    }
}
