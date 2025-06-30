class AddressAutofill {

    static init(addressGroupId) {
        return new AddressAutofill(addressGroupId);
    }

    constructor(addressGroupId) {
        this.group = document.getElementById(addressGroupId);
        this.fields = this.getFields();
        this.zipCodeField = document.querySelector("atlas-postal-code");
        this.zipCodeField.addEventListener("atlas-input-change", this.onZipCodeFieldChange.bind(this));
    }

    async onZipCodeFieldChange(event) {
        if (event.detail?.trim().length !== 9) return;
        const response = await this.fetchAddressByZipCode(event.detail);

        if (response.success) {
            this.fillFields(response.data);
            this.setOpenedGroup(true);
        } else {
            this.setOpenedGroup(false);
            this.clearFields();
            event.target.value = "";
            alert(response.message);
        }
    }

    parseFromViaCEPApiResponse(response) {
        const { logradouro, localidade, bairro, uf } = response;
        return { address: logradouro, city: localidade, neighborhood: bairro, state: uf };
    }

    async fetchAddressByZipCode(zipCode) {
        try {
            const response = await fetch(`https://viacep.com.br/ws/${zipCode}/json`);
            if (response.status === 400) throw new Error("O CEP digitado é inválido, digite um CEP válido!");
            const json = await response.json();

            if (json?.erro || response.status === 404) {
                throw new Error("Endereço não encontrado, digite um CEP existente!");
            }

            return { success: true, data: this.parseFromViaCEPApiResponse(json) };
        } catch (error) {
            const defaultMessage = "Ocorreu um erro ao tentar localizar o endereço com base no CEP digitado.";
            return { success: false, message: error.message || defaultMessage };
        }
    };

    setOpenedGroup(opened) {
        const group = this.group;
        group.setAttribute("opened", opened ? "true" : "false");
    }

    getFields() {
        const group = this.group;
        const addressFields = group.querySelectorAll("[data-autofill='true']");
        return Array.from(addressFields);
    };

    fillFields(addressData) {
        this.fields
            .filter(f => f.name in addressData)
            .forEach(f => f.value = addressData[f.name]);
    };

    clearFields() {
        const addressFields = this.getFields();
        if (!addressFields) return;
        addressFields.forEach(f => f.value = "");
    };

}

document.addEventListener("DOMContentLoaded", () => {
    AddressAutofill.init("address-group");
});