const atlasFilterForm = document.querySelector("atlas-filter-form");
const baseUrl = atlasFilterForm.getAttribute("data-base-url");

atlasFilterForm.addEventListener("atlas-apply-filter", (e) => {
    const filters = {};
    if (e.detail.filterData.status) filters.status = e.detail.filterData.status;
    const urlParams = new URLSearchParams(filters).toString();
    window.location.href = `${baseUrl}?${urlParams}`;
});

atlasFilterForm.addEventListener("atlas-clean-filter", (e) => {
    window.location.href = baseUrl;
});