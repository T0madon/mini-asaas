<%
	Map address = [
			zipCode      : params.postalCode,
			address      : params.address,
			addressNumber: params.addressNumber,
			complement   : params.complement,
			province     : params.neighborhood,
			city         : params.city,
			state        : params.state,
	]
%>

<atlas-form-step name="step-two" data-step="2">
    <atlas-section header="Endereço Principal" header-size="h5">
        <atlas-layout gap="4">
            <atlas-text muted size="xs">
				Informe os dados abaixo com atenção para que não haja alterações no decorrer do cadastro.
            </atlas-text>
            
            <g:render template="/templates/address-group" model= "[address: address]" />
            
        </atlas-layout>
    </atlas-section>

    <div class="form-step-actions">
        <atlas-button theme="secondary" description="Voltar" data-form-prev></atlas-button>
        <atlas-button theme="success" description="Finalizar Cadastro" submit></atlas-button>
    </div>
</atlas-form-step>