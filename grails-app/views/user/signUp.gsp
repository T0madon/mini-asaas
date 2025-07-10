<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="create.css"/>
    <title>Crie sua Conta - Mini Asaas</title>
</head>
<body>
    <header class="header">
        <asset:image src="asaas-white.svg" alt="Asaas Logo" class="logo"/>
    </header>

    <main>
        <section class="form-section">
            <g:if test="${flash.message}">
            	<atlas-alert type="${flash.status}" message="${flash.message}"></atlas-alert>
            </g:if>

            <atlas-form 
                id="registrationForm" 
                method="post" 
                action="${createLink(controller: 'user', action: 'save')}">

                <g:render template="templates/identityStep"/>
                <g:render template="templates/addressStep"/>
                
            </atlas-form>
        </section>

        <section class="image-section">
            <atlas-layout gap="2">
                <atlas-heading size="h2" theme="primary">Cadastre-se em menos de 5 minutos!</atlas-heading>
                <atlas-text class="subtitle" theme="primary" muted size="xsm">
                Abra sua conta e aproveite a solução mais <br/>
                completa e segura em emissão de cobranças <br/>
                e serviços financeiros.
                </atlas-text>
            </atlas-layout>
            <asset:image src="login-asaas-preview.png" alt="Mini Asaas Preview"/>
        </section>
    </main>

    <asset:javascript src="radio_control.js"/>
    <asset:javascript src="form-step-control.js"/>
    
</body>
</html>     