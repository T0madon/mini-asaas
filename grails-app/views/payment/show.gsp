<%@ page import="com.mini.asaas.payment.BillingType"%>
<%@ page import="com.mini.asaas.payment.PaymentStatus" %>
<%@ page import="com.mini.asaas.utils.DateFormatUtils" %>
<%@ page import="com.mini.asaas.utils.StringUtils" %>

<html>
<head>
    <title>Detalhes da cobrança</title>
    <meta name="layout" content="main">
</head>

<body>
<g:if test="${flash.message}">
    <atlas-alert type="${flash.status}" message="${flash.message}"></atlas-alert>
</g:if>

<atlas-form-panel header="Detalhes da cobrança" description="" submit-button-label=""
                  action="${createLink(controller: "payment", action: "update")}">
    <atlas-input
            value="${payment.id}"
            name="id"
            hidden>
    </atlas-input>

    <atlas-button slot="actions" description="Lista de Cobranças" icon="arrow-left"
                  href="${createLink(controller: "payment", action: "index")}">
    </atlas-button>

    <g:if test="${payment.status.canBeReceived()}">
        <atlas-button slot="actions" description="Receber" icon="money" theme="success"
                      href="${createLink(controller: "payment", action: "receive", params: [id: payment.id])}">
        </atlas-button>
    </g:if>

    <g:if test="${payment.status.canBeDeleted()}">
        <atlas-button slot="actions" description="Excluir" icon="trash" theme="danger"
                      href="${createLink(controller: "payment", action: "delete", params: [id: payment.id])}">
        </atlas-button>
    </g:if>

    <g:if test="${payment.deleted || payment.status == PaymentStatus.CANCELED || payment.status == PaymentStatus.OVERDUE}">
        <atlas-button slot="actions" description="Restaurar" icon="refresh" theme="danger"
                      href="${createLink(controller: "payment", action: "restore", params: [id: payment.id])}">
        </atlas-button>
    </g:if>

    <g:if test="${payment.status != PaymentStatus.RECEIVED}">
        <atlas-button slot="actions" description="Editar" icon="pencil" data-panel-start-editing="true"></atlas-button>
    </g:if>
    <g:else>
        <atlas-button slot="actions" description="Comprovante" icon="file-text" theme="primary"
                      href="${createLink(controller: "payment", action: "receipt", params: [id: payment.id])}">
        </atlas-button>
    </g:else>

    <atlas-grid>
        <atlas-row>
            <atlas-col lg="7">
                <atlas-input
                        name="payerId"
                        value="${payment.payerId}"
                        hidden>
                </atlas-input>
                <atlas-input
                        label="Pagador"
                        value="${payment.payer.name}"
                        required
                        readonly>
                </atlas-input>
            </atlas-col>
            <atlas-col lg="5">
                <atlas-money
                        label="Valor da cobrança"
                        name="value"
                        id="value"
                        value="${StringUtils.fromBigDecimal(payment.value)}"
                        required>
                </atlas-money>
            </atlas-col>
        </atlas-row>

        <atlas-row>
            <atlas-col lg="12">
                <atlas-input
                        label="Descrição"
                        type="text"
                        name="description"
                        value="${payment.description}">
                </atlas-input>
            </atlas-col>
        </atlas-row>

        <atlas-row>
            <atlas-col lg="6">
                <atlas-date-picker
                        label="Data de vencimento"
                        value="${DateFormatUtils.format(payment.dueDate)}"
                        name="dueDate"
                        required>
                </atlas-date-picker>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-select
                        label="Forma de pagamento"
                        placeholder="Selecione uma opção"
                        name="billingType"
                        value="${payment.billingType}"
                        required>
                    <g:each in="${BillingType.values()}" var="type">
                        <atlas-option label="${type.getLabel()}" value="${type.name()}"></atlas-option>
                    </g:each>
                </atlas-select>
            </atlas-col>
        </atlas-row>

        <atlas-row>
            <atlas-col lg="6">
                <atlas-input
                        label="Status"
                        value="${payment.status.getLabel()}"
                        required
                        readonly>
                </atlas-input>
                <atlas-input
                        name="status"
                        value="${payment.status.name()}"
                        hidden>
                </atlas-input>
            </atlas-col>
            <atlas-col lg="6">
                <atlas-input
                        label="Data de pagamento"
                        name="paymentDate"
                        value="${DateFormatUtils.format(payment.paymentDate)}"
                        readonly>
                </atlas-input>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-form-panel>
</body>
</html>