<!doctype html>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><g:layoutTitle default="Mini Asaas"/></title>

    <link rel="stylesheet" href="https://atlas.asaas.com/atlas.min.css" crossorigin="anonymous"/>

    <script src="https://atlas.asaas.com/atlas.min.js" crossorigin="anonymous"></script>

    <asset:link rel="shortcut icon" href="favicon-shortcut.ico" type="image/x-icon"/>

    <g:layoutHead/>
</head>
<body class="has-atlas">
    <atlas-screen>
        <g:render template="/templates/sidebar" />
        <atlas-page class="js-atlas-page">
            <atlas-page-header>
                <atlas-breadcrumb slot="breadcrumb">
                    <atlas-breadcrumb-item text="${pageProperty(name: "body.page-title")}" icon="home">
                    </atlas-breadcrumb-item>
                </atlas-breadcrumb>
            </atlas-page-header>

            <atlas-page-content slot="content" class="js-atlas-content">
                <g:layoutBody />
            </atlas-page-content>
        </atlas-page>
    </atlas-screen>
</body>
</html> 