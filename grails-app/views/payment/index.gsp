<%@ page import="com.mini.asaas.utils.DateFormatUtils; com.mini.asaas.utils.StringUtils; com.mini.asaas.payment.PaymentStatus" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Minhas Cobranças</title>
    <meta name="layout" content="main">
</head>

<body page-title="Minhas Cobranças">
<atlas-panel>
    <atlas-filter>
        <atlas-filter-form
                slot="simple-filter"
                data-base-url="${createLink(controller: "payment", action: "index")}">
            <atlas-filter-group name="status">
                <atlas-checkbox value="${PaymentStatus.PENDING}">Aguardando pagamento</atlas-checkbox>
                <atlas-checkbox value="${PaymentStatus.RECEIVED}">Recebidas</atlas-checkbox>
                <atlas-checkbox value="${PaymentStatus.OVERDUE}">Vencidas</atlas-checkbox>
                <atlas-checkbox value="${PaymentStatus.CANCELED}">Canceladas</atlas-checkbox>
            </atlas-filter-group>
        </atlas-filter-form>
    </atlas-filter>

    <g:if test="${paymentList}">
        <atlas-toolbar>
            <atlas-button
                    icon="plus"
                    description="Criar cobrança"
                    href="${createLink(controller: "createPayment", action: "index")}"
                    slot="actions">
            </atlas-button>
        </atlas-toolbar>
        <atlas-table has-actions>
            <atlas-table-header slot="header">
                <atlas-table-col>Nome do pagador</atlas-table-col>
                <atlas-table-col>Valor</atlas-table-col>
                <atlas-table-col>Forma de pagamento</atlas-table-col>
                <atlas-table-col>Data de vencimento</atlas-table-col>
                <atlas-table-col>Status</atlas-table-col>
            </atlas-table-header>

            <atlas-table-body slot="body">
                <g:each var="payment" in="${paymentList}">
                    <atlas-table-row
                            href="${createLink(controller: "payment", action: "show", id: payment.id)}">
                        <atlas-table-col>${payment.payer.name}</atlas-table-col>
                        <atlas-table-col>R$${StringUtils.fromBigDecimal(payment.value)}</atlas-table-col>
                        <atlas-table-col>${payment.billingType.getLabel()}</atlas-table-col>
                        <atlas-table-col>${DateFormatUtils.format(payment.dueDate)}</atlas-table-col>
                        <atlas-table-col>
                            <paymentTagLib:atlasBadge status="${payment.status}" />
                        </atlas-table-col>
                    </atlas-table-row>
                </g:each>
            </atlas-table-body>
        </atlas-table>
    </g:if>
    <g:else>
        <atlas-empty-state
                illustration="flow-money-coins"
                header="Sem cobranças cadastradas">
            Aqui você pode criar cobranças para os seus clientes.
            <atlas-button
                    icon="plus"
                    description="Criar cobrança"
                    href="${createLink(controller: "createPayment", action: "index")}"
                    slot="button"></atlas-button>
        </atlas-empty-state>
    </g:else>
    <g:render template="/templates/pages" model="${[total: total, limitPage: limitPage]}" />
</atlas-panel>

<asset:javascript src="form-filters.js" />
</body>
</html>