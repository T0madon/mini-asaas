<atlas-offcanvas
        header="Notificações"
        has-button-block
        id="notification-center"
>
    <atlas-input type="hidden" class="js-notification-url" value="${createLink(controller: 'notification', action: 'list')}"></atlas-input>
    <atlas-layout gap="2">
        <atlas-notification-center-group>
            <g:if test="${ notifications }">
                <g:each in="${ notifications }" var="notification">
                    <g:render template="/templates/notification/notification-card" model="${[notification: notification]}"/>
                </g:each>
            </g:if>
            <g:else>
                <atlas-empty-state
                        icon="alert-circle"
                        header="Sem notificações"
                >
                    Aqui aparecerão as notificações do sistema.
                </atlas-empty-state>
            </g:else>
        </atlas-notification-center-group>
    </atlas-layout>

    <atlas-button
            slot="actions"
            description="Marcar todas como lidas"
            block
            type="ghost"
            icon="double-check"
            size="sm"
            href="${createLink(controller: "notification", action: "deleteAll")}"
    >
    </atlas-button>
</atlas-offcanvas>