<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Map address = [
            postalCode   : params.postalCode,
            address      : params.address,
            addressNumber: params.addressNumber,
            complement   : params.complement,
            neighborhood : params.neighborhood,
            city         : params.city,
            state        : params.state,
    ]
%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Cadastrar pagador</title>
</head>

<body>
<g:if test="${flash.message}">
    <atlas-alert type="${flash.status}" message="${flash.message}"></atlas-alert>
</g:if>
<g:if
        test="${flash.code == "alreadyExistsAndDeleted.cpfCnpj" || flash.code == "alreadyExistsAndDeleted.email"}">
    <atlas-helper message="Deletar pagador"
                  href="${createLink(controller: "payer", action: "deleteOrRestore", params: [cpfCnpj: params.cpfCnpj, email: params.email])}">
    </atlas-helper>
</g:if>
<g:if test="${flash.code == "alreadyExistsAndView.cpfCnpj" || flash.code == "alreadyExistsAndView.email"}">
    <atlas-helper message="Visualizar pagador"
                  href="${createLink(controller: "payer", action: "show", params: [cpfCnpj: params.cpfCnpj, email: params.email])}">
    </atlas-helper>
</g:if>

<atlas-panel header="Cadastrar pagador">
    <atlas-form class="form" action="${createLink(controller: "payer", action: "save")}">
        <atlas-grid>
            <atlas-row>
                <atlas-col lg="6">
                    <atlas-input
                            label="Nome"
                            type="text"
                            name="name"
                            id="name"
                            value="${params.name}"
                            required>
                    </atlas-input>
                    <g:hasErrors bean="${errors}" field="name">
                        <span class="form--error">
                            <g:renderErrors bean="${errors}" field="name" />
                        </span>
                    </g:hasErrors>
                </atlas-col>
                <atlas-col lg="6">
                    <atlas-masked-input
                            col="2"
                            mask-alias="email"
                            label="E-mail"
                            type="email"
                            name="email"
                            id="email"
                            value="${params.email}"
                            required>
                    </atlas-masked-input>
                    <g:hasErrors bean="${errors}" field="email">
                        <span class="form--error">
                            <g:renderErrors bean="${errors}" field="email" />
                        </span>
                    </g:hasErrors>
                </atlas-col>
            </atlas-row>

            <atlas-row>
                <atlas-col lg="4">
                    <atlas-masked-input
                            mask-alias="cpf-cnpj"
                            label="CPF/CNPJ"
                            type="text"
                            name="cpfCnpj"
                            id="cpfCnpj"
                            value="${params.cpfCnpj}"
                            required>
                    </atlas-masked-input>
                    <g:hasErrors bean="${errors}" field="cpfCnpj">
                        <span class="form--error">
                            <g:renderErrors bean="${errors}" field="cpfCnpj" />
                        </span>
                    </g:hasErrors>
                </atlas-col>
                <atlas-col lg="4">
                    <atlas-masked-input
                            mask-alias="phone"
                            label="Número de Telefone"
                            type="tel"
                            name="phoneNumber"
                            id="phoneNumber"
                            value="${params.phoneNumber}"
                            required>
                    </atlas-masked-input>
                    <g:hasErrors bean="${errors}" field="phoneNumber">
                        <span class="form--error">
                            <g:renderErrors bean="${errors}" field="phoneNumber" />
                        </span>
                    </g:hasErrors>
                </atlas-col>
            </atlas-row>

            <g:render template="/templates/address-group" model="${[address: address, opened: false]}"/>

            <atlas-button submit="true" description="Cadastrar" type="filled"></atlas-button>
        </atlas-grid>
    </atlas-form>
</atlas-panel>
</body>
</html>