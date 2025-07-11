document.addEventListener('DOMContentLoaded', function() {

    const selectorContainer = document.getElementById('person-type-selector');
    if (!selectorContainer) {
        return;
    }

    const sectionsConfig = {
        'NATURAL': {
            section: document.getElementById('cpf-section'),
            input: document.querySelector('atlas-masked-input[name="cpf"]')
        },
        'LEGAL': {
            section: document.getElementById('cnpj-section'),
            input: document.querySelector('atlas-masked-input[name="cnpj"]')
        }
    };

    const updateVisibleSection = (selectedValue) => {
        for (const key in sectionsConfig) {

            const config = sectionsConfig[key];
            const isActive = (key === selectedValue);

            if (config.section && config.input) {
                config.section.style.display = isActive ? 'block' : 'none';
                config.input.disabled = !isActive;
            }
        }
    };

    selectorContainer.addEventListener('click', function(event) {
        const card = event.target.closest('atlas-selection-card');
        if (!card) return;

        const selectedValue = card.getAttribute('value');
        if (selectedValue) {
            updateVisibleSection(selectedValue);
        }
    });
    
    const initialSelectedCard = document.querySelector('atlas-selection-card[name="personTypeString"][checked]');
    const initialValue = initialSelectedCard ? initialSelectedCard.getAttribute('value') : 'NATURAL';
    updateVisibleSection(initialValue);
});
