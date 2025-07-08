document.addEventListener('DOMContentLoaded', function() {
    const steps = document.querySelectorAll('atlas-form-step');
    
    const nextButtons = document.querySelectorAll('[data-form-next]');
    const prevButtons = document.querySelectorAll('[data-form-prev]');
    
    let currentStep = 1;

    function showStep(stepNumber) {
        steps.forEach(step => {
            step.style.display = 'none';
        });

        const activeStep = document.querySelector(`atlas-form-step[data-step="${stepNumber}"]`);
        if (activeStep) {
            activeStep.style.display = 'block';
            currentStep = stepNumber;
        }
    }

    nextButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            showStep(currentStep + 1);
        });
    });

    prevButtons.forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            showStep(currentStep - 1);
        });
    });

    showStep(1);
});