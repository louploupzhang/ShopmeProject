$(document).ready(function () {
    $("#logoutLink").on("click", function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    })

    customizeDropDownMenu();
})

function customizeDropDownMenu() {
    // Fix the dropdown menu sliding animation
    $(".navbar .dropdown").hover(function () { //Slide down
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function () { //Slide up
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        })

    $(".dropdown > a").click(function () {
        location.href = this.href;
    })
}