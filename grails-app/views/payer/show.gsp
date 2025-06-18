<%@ page import="com.mini.asaas.utils.DateFormatUtils" %>
<%
    Map address = [
            postalCode   : payer.postalCode,
            address      : payer.address,
            addressNumber: payer.addressNumber,
            complement   : payer.complement,
            neighborhood : payer.neighborhood,
            city         : payer.city,
            state        : payer.state,
    ]
%>

<html>
<head>
    <title>Detalhes do Pagador</title>
    <meta name="layout" content="main">
</head>

<body>
<g:if test="${flash.message}">
    <atlas-alert type="${flash.status}" message="${flash.message}"></atlas-alert>
</g:if>
<g:if test="${flash.code == "alreadyExistsAndDeleted.cpfCnpj" || flash.code == "alreadyExistsAndDeleted.email"}">
    <atlas-helper message="Restaurar pagador"
                  href="${createLink(controller: "payer", action: "restore")}">
    </atlas-helper>
</g:if>
<g:if test="${flash.code == "alreadyExistsAndView.cpfCnpj" || flash.code == "alreadyExistsAndView.email"}">
    <atlas-helper message="Visualizar pagador"
                  href="${createLink(controller: "payer", action: "index")}">
    </atlas-helper>
</g:if>

<atlas-form-panel header="Detalhes do pagador" description="" submit-button-label=""
                  action="${createLink(controller: "payer", action: "update", params: [id: payer.id])}"
                  method="post">
    <atlas-input
            value="${payer.id}"
            name="id"
            hidden>
    </atlas-input>

    <atlas-button slot="actions" description="Editar" icon="pencil" data-panel-start-editing="true"></atlas-button>
    <atlas-button slot="actions" description="Excluir" icon="trash" theme="danger"
                  href="${createLink(controller: "payer", action: "delete", params: [id: payer.id])}">
    </atlas-button>

    <atlas-grid>
        <atlas-row>
            <atlas-col lg="6">
                <atlas-layout gap="6">
                    <atlas-text size="md" bold="" underline="">Dados comerciais</atlas-text>

                    <atlas-row>
                        <atlas-col lg="12">
                            <atlas-input
                                    label="Nome"
                                    name="name"
                                    required
                                    value="${payer.name}">
                            </atlas-input>
                        </atlas-col>
                    </atlas-row>

                    <atlas-row>
                        <atlas-col lg="12">
                            <atlas-masked-input
                                    label="Email"
                                    name="email"
                                    mask-alias="email"
                                    value="${payer.email}"
                                    required>
                            </atlas-masked-input>
                        </atlas-col>
                    </atlas-row>

                    <atlas-row>
                        <atlas-col lg="12">
                            <atlas-input
                                    label="Natureza Jurídica"
                                    name="personType"
                                    readonly
                                    value="${payer.personType.getLabel()}"
                                    required>
                            </atlas-input>
                        </atlas-col>
                    </atlas-row>

                    <atlas-row>
                        <atlas-col lg="12">
                            <atlas-masked-input
                                    mask-alias="cpf-cnpj"
                                    label="CPF/CNPJ"
                                    name="cpfCnpj"
                                    value="${payer.cpfCnpj}"
                                    required>
                            </atlas-masked-input>
                        </atlas-col>
                    </atlas-row>

                </atlas-layout>
            </atlas-col>

            <atlas-col lg="6">
                <atlas-layout gap="5">
                    <atlas-text size="md" bold="" underline="">Dados de endereço</atlas-text>
                    <g:render template="/templates/address-group" model="${[address: payer, opened: true]}"/>
                </atlas-layout>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-form-panel>
</body>
</html>