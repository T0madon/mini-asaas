<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.mini.asaas.utils.CpfCnpjUtils" %>
<html>
<head>
    <title>Pagadores Ativos</title>
    <meta name="layout" content="main">
</head>

<body page-title="Lista de pagadores ativos">
<atlas-panel>
    <g:if test="${payerList}">
        <atlas-toolbar>
            <atlas-button
                    icon="plus"
                    description="Adicionar pagador"
                    href="${createLink(controller: "payer", action: "create")}"
                    slot="actions">
            </atlas-button>
        </atlas-toolbar>
        <atlas-table has-actions>
            <atlas-table-header slot="header">
                <atlas-table-col>Nome</atlas-table-col>
                <atlas-table-col>CPF/CNPJ</atlas-table-col>
                <atlas-table-col>E-mail</atlas-table-col>
            </atlas-table-header>

            <atlas-table-body slot="body">
                <g:each var="payer" in="${payerList}">
                    <atlas-table-row href="${createLink(controller: "payer", action: "show", id: payer.id)}">
                        <atlas-table-col>${payer.name}</atlas-table-col>
                        <atlas-table-col>${CpfCnpjUtils.formatCpfCnpj(payer.cpfCnpj)}</atlas-table-col>
                        <atlas-table-col>${payer.email}</atlas-table-col>
                        <atlas-table-col>
                            <atlas-button-group>
                                <atlas-button icon="pencil" type="ghost" size="sm"></atlas-button>
                                <atlas-button icon="trash" type="ghost" size="sm" theme="danger"
                                              href="${createLink(controller: "payer", action: "delete", params: [id: payer.id])}"></atlas-button>
                            </atlas-button-group>
                        </atlas-table-col>
                    </atlas-table-row>
                </g:each>
            </atlas-table-body>
        </atlas-table>
    </g:if>
    <g:else>
        <atlas-empty-state
                illustration="schedule-user-avatar"
                header="Sem pagadores cadastrados">
            Cadastrar os pagadores que você deseja utilizar em suas transações.
            <atlas-button
                    icon="plus"
                    description="Adicionar pagador"
                    href="${createLink(controller: "payer", action: "create")}"
                    slot="button"></atlas-button>
        </atlas-empty-state>
    </g:else>
</atlas-panel>
</body>
</html>