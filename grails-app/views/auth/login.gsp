<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <asset:stylesheet src="login.css"/>
  <title>Login</title>
</head>
<body>
  <header class="header">
  <asset:image src="asaas-white.svg" alt="Asaas Logo" class="logo"/>
  </header>

  <main>
    <section class="login-section">
      <atlas-layout gap="2">
        <atlas-heading size="h5">Acesso ao Asaas</atlas-heading>
        <atlas-text class="subtitle" theme="primary" muted size="xsm">
          Olá, use os campos abaixo para acessar sua conta Mini Asaas.
        </atlas-text>
      </atlas-layout>

      <atlas-form method="post" action="${createLink(controller:'auth', action:'authenticate')}">
        <g:if test="${flash.message}">
            <atlas-alert type="${flash.type?.getValue() ?: 'error'}" message="${flash.message}" closable></atlas-alert>
        </g:if>
        <atlas-layout gap="4">
          <atlas-masked-input
            label="E-mail" name="email" type="email" placeholder="Informe seu e-mail" size="sm" value="${params.email}" required>
          </atlas-masked-input>
          <atlas-password-input
            label="Senha" name="password" placeholder="Informe sua senha" size="sm" value="${params.password}" required>
          </atlas-password-input>
        </atlas-layout>
        <atlas-button
          theme="success" description="Acessar conta" type="filled" size="sm" submit="" block="">
        </atlas-button>
      </atlas-form>

      <atlas-text class="subtitle" theme="primary" muted size="xsm">
        Ainda não possui uma conta?
      </atlas-text>

      <atlas-button class="button_create" type="outlined" size="sm" theme="secondary" description="Criar conta" href="/user/create">
      </atlas-button>
    </section>

    <section class="image-section">
      <atlas-layout gap="2">
        <atlas-heading size="h2" theme="primary">
          Seja bem-vindo ao Asaas!
        </atlas-heading>
        <atlas-text class="subtitle" theme="primary" muted size="xsm">
          A solução mais completa e segura em emissão de cobranças
          e serviços financeiros.
        </atlas-text>
      </atlas-layout>

      <asset:image src="login-asaas-preview.png" alt="Mini Asaas Preview"/>
    </section>
  </main>
</body>
</html>
