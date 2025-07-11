<%@ page import="java.time.LocalDateTime; java.time.format.DateTimeFormatter; com.mini.asaas.utils.StringUtils" contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-BR">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Cobrança ${actionMessage}!</title>
    <style type="text/css">
    @media screen and (max-width: 600px) {
        .content {
            width: 100% !important;
        }
    }
    </style>
</head>
<body style="margin: 0; padding: 0; background-color: #f4f7f6;">
<table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color: #f4f7f6;">
    <tr>
        <td align="center" style="padding: 20px 0;">
            <table class="content" align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="width: 100%; max-width: 600px; border-collapse: collapse; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1);">
                <tr>
                    <td align="center" style="padding: 30px 40px 20px 40px;">
                        <img src="https://github.com/T0madon/mini-asaas/blob/main/grails-app/assets/images/logo-asaas-azul.png" alt="Logo Mini-Asaas" width="150" style="display: block; border: 0;" />
                    </td>
                </tr>
                <tr>
                    <td style="padding: 0 40px 30px 40px; color: #333333; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.6;">
                        <h2 style="color: #0d263e; font-size: 22px; margin: 0 0 20px 0;">
                            Olá! Uma nova ação de cobrança ocorreu!
                        </h2>
                        <p style="margin: 0 0 15px 0;">
                            Uma cobrança foi <strong>${actionMessage}</strong>! Detalhes abaixo:
                        </p>
                        <div style="border-top: 1px solid #eeeeee; padding-top: 15px;">
                            ${body}
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td align="center" style="padding: 10px 0;">
            <p style="color: #888888; font-family: Arial, sans-serif; font-size: 12px; margin: 0;">
                Este é um e-mail automático. Por favor, não responda.<br/>
                Enviado em ${java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}
            </p>
        </td>
    </tr>
</table>
</body>
</html>

