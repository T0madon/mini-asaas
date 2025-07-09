<atlas-sidebar slot="sidebar">
    <atlas-sidebar-header slot="header">
        <atlas-button
                block=""
                icon="plus"
                description="Criar cobrança"
                href="${createLink(controller: "createPayment", action: "index")}"
                slot="actions">
        </atlas-button>
    </atlas-sidebar-header>

    <atlas-sidebar-menu slot="body">
        <atlas-sidebar-menu-item
                icon="users"
                value="clients-group"
                text="Pagadores"
            ${ controllerName == "payer" ? "active" : "" }>
            <atlas-sidebar-menu-item
                    icon="user"
                    text="Cadastrar pagador"
                    href="${createLink(controller: "payer", action: "create")}"
                ${ controllerName == "payer" && actionName == "create" ? "active" : "" }
            ></atlas-sidebar-menu-item>
            <atlas-sidebar-menu-item
                    icon="users"
                    value="clients-group"
                    text="Pagadores ativos"
                    href="${createLink(controller: "payer", action: "index")}"
                ${ controllerName == "payer" && actionName == "index" ? "active" : "" }
            ></atlas-sidebar-menu-item>
            <atlas-sidebar-menu-item
                    icon="users"
                    value="clients-group"
                    text="Pagadores inativos"
                    href="${createLink(controller: "payer", action: "showDeleted")}"
                ${ controllerName == "payer" && actionName == "showDeleted" ? "active" : "" }
            ></atlas-sidebar-menu-item>
        </atlas-sidebar-menu-item>
        <atlas-sidebar-menu-item
                icon="money"
                text="Cobranças"
            ${ controllerName == "payment" ? "active" : "" }>
            <atlas-sidebar-menu-item
                    icon="files"
                    text="Todas"
                    href="${createLink(controller: "payment", action: "index")}"
                ${ controllerName == "payment" && actionName == "index" ? "active" : "" }
            ></atlas-sidebar-menu-item>
        </atlas-sidebar-menu-item>
        <atlas-sidebar-menu-item
                icon="bell"
                text="Notificações"
                href="${createLink(controller: "notification", action: "list")}"
            ${ controllerName == "notification" && actionName == "list" ? "active" : "" }
        >
        </atlas-sidebar-menu-item>
    </atlas-sidebar-menu>
</atlas-sidebar>