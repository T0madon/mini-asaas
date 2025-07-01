<%@ page import="com.mini.asaas.utils.CpfCnpjUtils" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Pagadores inativos</title>
    <meta name="layout" content="main">
</head>

<body>
<atlas-panel header="Lista de pagadores inativos">
    <atlas-button slot="actions" description="Pagadores Ativos" icon="arrow-left"
                  href="${createLink(controller: "payer", action: "index")}">
    </atlas-button>
    <g:if test="${ payerList }">
        <atlas-table has-actions>
            <atlas-table-header slot="header">
                <atlas-table-col>Nome</atlas-table-col>
                <atlas-table-col>CPF/CNPJ</atlas-table-col>
                <atlas-table-col>E-mail</atlas-table-col>
            </atlas-table-header>

            <atlas-table-body slot="body">
                <g:each var="payer" in="${ payerList }">
                    <atlas-table-row>
                        <atlas-table-col>${payer.name}</atlas-table-col>
                        <atlas-table-col>${CpfCnpjUtils.formatCpfCnpj(payer.cpfCnpj)}</atlas-table-col>
                        <atlas-table-col>${payer.email}</atlas-table-col>
                        <atlas-table-col>
                            <atlas-button icon="refresh" type="ghost" size="sm" theme="danger"
                                          href="${createLink(controller: "payer", action: "deleteOrRestore", params: [id: payer.id])}">
                            </atlas-button>
                        </atlas-table-col>
                    </atlas-table-row>
                </g:each>
            </atlas-table-body>
        </atlas-table>
    </g:if>
    <g:else>
        <atlas-empty-state
                illustration="schedule-user-avatar"
                header="Sem pagadores inativos">
            Aqui você pode vizualizar os pagadores que foram deletados.
            <atlas-button
                    description="Pagadores ativos"
                    href="${createLink(controller: "payer", action: "index")}"
                    slot="button"
            ></atlas-button>
        </atlas-empty-state>
    </g:else>
    <g:render template="/templates/pages" model="${[total: total, limitPage: limitPage]}" />
</atlas-panel>
</body>
</html>