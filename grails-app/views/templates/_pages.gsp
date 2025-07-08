<% params.page = params.page  ? params.page as Integer : 1 %>

<atlas-layout alignment="center" class="js-pagination" inline justify="center" gap="2">
    <g:if test="${ params.page > 1 }">
        <atlas-button
                type="outlined"
                href="${createLink(controller: controllerName , action: actionName, params: params + [page: params.page - 1])}"
                description="${ params.page - 1 }">
        </atlas-button>
    </g:if>
    <atlas-button
            href="${createLink(controller: controllerName , action: actionName, params: params)}"
            description="${ params.page }">
    </atlas-button>
    <g:if test="${ total > limitPage * params.page }">
        <atlas-button
                type="outlined"
                href="${createLink(controller: controllerName , action: actionName, params: params + [page: params.page + 1])}"
                description="${ params.page + 1 }">
        </atlas-button>
    </g:if>
</atlas-layout>