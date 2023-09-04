var dropDownCountry;
var dateListState;
var fieldState;

$(document).ready(function () {
    dropDownCountry = $("#country");
    dateListState = $("#listStates");
    fieldState = $("#state");

    dropDownCountry.on("change", function () {
        loadStatesForCountry();
        fieldState.val("").focus();
    })
})

function loadStatesForCountry() {
    selectedCountry = $("#country option:selected");
    countryId = selectedCountry.val();
    url = contextPath + "settings/list_states_by_country/" + countryId;

    $.get(url, function (responseJSON) {
        dateListState.empty();

        $.each(responseJSON, function (index, state) {
            $("<option>").val(state.name).text(state.name).appendTo(dateListState);
        })
    }).fail(function() {
        alert('Failed to connect to the server!');
    });
}

function checkPasswordMatch(confirmPassword) {
    if (confirmPassword.value != $("#password").val()) {
        confirmPassword.setCustomValidity("Passwords do not match!")
    } else {
        confirmPassword.setCustomValidity("");
    }
}