<%@ page import="com.mini.asaas.payment.BillingType" contentType="text/html;charset=UTF-8" %>

<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Criar cobrança</title>
</head>

<body>
<atlas-page class="js-atlas-page">
    <atlas-page-header page-name="Dados da cobrança" slot="header"></atlas-page-header>

    <atlas-page-content slot="content" class="js-atlas-content">
        <g:if test="${flash.message}">
            <atlas-alert
                    type="${flash.success ? 'success' : 'error'}"
                    message="${flash.message}"
                    class="js-atlas-alert"
            ></atlas-alert>
        </g:if>

        <atlas-form class="form" action="${createLink(controller: "payment", action: "save")}">
            <atlas-button class="" description="Lista de Cobranças" icon="arrow-left"
                          href="${createLink(controller: "payment", action: "index")}">
            </atlas-button>
            <atlas-grid>
                <atlas-row>
                    <atlas-col lg="7">
                        <atlas-select
                                label="Pagador"
                                placeholder="Selecione um pagador"
                                name="payerId"
                                value="${params.payerId}"
                                required>
                            <g:each in="${payerList}" var="payer">
                                <atlas-option label="${payer.name}" value="${payer.id}"></atlas-option>
                            </g:each>
                        </atlas-select>
                    </atlas-col>
                    <atlas-col lg="5">
                        <atlas-money
                                label="Valor da cobrança"
                                name="value"
                                id="value"
                                value="${params.value}"
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
                                value="${params.description}">
                        </atlas-input>
                    </atlas-col>
                </atlas-row>

                <atlas-row>
                    <atlas-col lg="6">
                        <atlas-date-picker
                                label="Data de vencimento"
                                value="${params.dueDate}"
                                name="dueDate"
                                required>
                        </atlas-date-picker>
                    </atlas-col>
                    <atlas-col lg="6">
                        <atlas-select
                                label="Forma de pagamento"
                                placeholder="Selecione uma opção"
                                name="billingType"
                                value="${params.billingType}"
                                required>
                            <g:each in="${BillingType.values()}" var="type">
                                <atlas-option label="${type.getLabel()}" value="${type.name()}"></atlas-option>
                            </g:each>
                        </atlas-select>
                    </atlas-col>
                </atlas-row>
            </atlas-grid>

            <atlas-button submit="true" description="Finalizar"></atlas-button>

            <atlas-button theme="danger" description="Cancelar" icon="x"
                          href="${createLink(controller: "createPayment", action: "index")}">
            </atlas-button>
        </atlas-form>
    </atlas-page-content>
</atlas-page>
</body>
</html>