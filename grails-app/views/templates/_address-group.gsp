<asset:stylesheet src="address-group" />

<div class="address-postal-code-area">
    <atlas-postal-code
            disable-search
            name="postalCode"
            label="CEP"
            value="${address.postalCode}"
            required>
    </atlas-postal-code>
    <atlas-link
            size="sm"
            icon="info"
            external
            block
            content-width
            href="https://buscacepinter.correios.com.br/app/endereco/index.php"
            title="Buscar CEP">
        Não sei o meu CEP
    </atlas-link>
</div>
<atlas-layout gap="3" id="address-group" opened="${opened ?: false}">
    <atlas-grid>
        <atlas-row>
            <atlas-col>
                <atlas-input
                        data-autofill="true"
                        type="text"
                        name="city"
                        label="Cidade"
                        value="${address.city}"
                        disabled
                        required>
                </atlas-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col>
                <g:render
                        template="/templates/select-address-state"
                        model="[value: address.state, autofill: true, disabled: true]" />
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col>
                <atlas-input
                        data-autofill="true"
                        type="text"
                        name="address"
                        label="Rua"
                        value="${address.address}"
                        required>
                </atlas-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col>
                <atlas-input
                        data-autofill="true"
                        type="text"
                        name="neighborhood"
                        label="Bairro"
                        value="${address.neighborhood}"
                        required>
                </atlas-input>
            </atlas-col>
        </atlas-row>
        <atlas-row>
            <atlas-col lg="4" md="2">
                <atlas-integer-input
                        data-autofill="true"
                        type="text"
                        name="addressNumber"
                        label="Nº"
                        placeholder="S/N"
                        value="${address.addressNumber}">
                </atlas-integer-input>
            </atlas-col>
            <atlas-col lg="8" md="4">
                <atlas-input
                        data-autofill="true"
                        type="text"
                        name="complement"
                        label="Complemento"
                        value="${address.complement}">
                </atlas-input>
            </atlas-col>
        </atlas-row>
    </atlas-grid>
</atlas-layout>

<asset:javascript src="address-autofill" />