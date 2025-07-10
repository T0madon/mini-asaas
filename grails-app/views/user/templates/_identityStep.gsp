<atlas-form-step name="step-one" data-step="1">
    <atlas-section header="Seus dados principais" header-size="h5">
        <atlas-layout gap="2">

            <atlas-layout gap="3">
                <atlas-input
                    type="text"
                    name="name"
                    label="Nome completo (Igual ao documento)"
                    value="${params.name}"
                    size="sm"
                    required>
                </atlas-input>

                <atlas-masked-input
                    mask-alias="email"
                    label="Email"
                    name="email"
                    type="email"
                    size="sm"
                    value="${params.email}"
                    required>
                </atlas-masked-input>

                <atlas-password-input
                    label="Senha"
                    name="password"
                    size="sm"
                    value="${params.password}"
                    required>
                </atlas-password-input>
            </atlas-layout>

            <div class="divider"></div>

            <atlas-layout gap="2">
                <atlas-text class="selection-card-group-label" bold size="xs">Eu sou</atlas-text>
                <atlas-layout direction="horizontal" gap="3" id="person-type-selector">
                    <atlas-selection-card 
                    label="Pessoa Física" 
                    type="radio" 
                    name="personTypeString" 
                    value="NATURAL" 
                    required 
                    checked>
                    Autônomo sem CNPJ
                    </atlas-selection-card>
                    <atlas-selection-card 
                    label="Pessoa Jurídica" 
                    type="radio" 
                    name="personTypeString" 
                    value="LEGAL" required>
                    MEI, LTDA, S/A, SS
                    </atlas-selection-card>
                </atlas-layout>
            </atlas-layout>

            <div id="cpf-section">
                <atlas-masked-input 
                mask-alias="cpf" 
                name="cpf" 
                label="CPF" 
                value="${params.cpf}" 
                required>
                </atlas-masked-input>
            </div>
            <div id="cnpj-section" style="display: none;">
                <atlas-masked-input 
                mask-alias="cnpj" 
                name="cnpj" 
                label="CNPJ" 
                value="${params.cnpj}" 
                required 
                disabled>
                </atlas-masked-input>
            </div>
            
        </atlas-layout>
    </atlas-section>

    <atlas-button class="form-step-single-action" description="Avançar" data-form-next></atlas-button>
</atlas-form-step>