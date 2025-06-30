<%@ page import="com.mini.asaas.utils.CpfCnpjUtils" %>
<%@ page import="com.mini.asaas.utils.DateFormatUtils" %>
<%@ page import="com.mini.asaas.utils.StringUtils" %>

<html>
<head>
    <title>Recibo de pagamento</title>
    <meta name="layout" content="basic">
    <asset:stylesheet src="payment/receipt.css"/>
</head>

    <body>
        <header class="header">
            <asset:image
                    class="header--logo"
                    src="asaas-white.svg"
                    alt="Logomarca Asaas"
                    title="Asaas Gestão Financeira"/>
        </header>

        <atlas-form-panel header="Comprovante de pagamento" class="container">
            <atlas-illustration name="bank" size="xs"></atlas-illustration>
            <h2>R$${StringUtils.fromBigDecimal(payment.value)}</h2>

            <div class="details">
                <h3>Informações da transação:</h3>
                <p>Data da transação: ${DateFormatUtils.formatWithTime(payment.paymentDate)}</p>
                <p>Descrição: ${payment.description}</p>
                <p>Forma de pagamento: ${payment.billingType.getLabel()}</p>

                <h3>Pagador:</h3>
                <p>Nome: ${payment.payer.name}</p>
                <p>CPF/CNPJ: ${CpfCnpjUtils.formatCpfCnpj(payment.payer.cpfCnpj)}</p>

                <h3>Quem recebeu:</h3>
                <p>Nome: ${payment.customer.name}</p>
                <p>CPF/CNPJ: ${CpfCnpjUtils.formatCpfCnpj(payment.customer.cpfCnpj)}</p>
            </div>
        </atlas-form-panel>
    </body>
</html>