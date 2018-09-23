$(function () {
    $('#search_form').on('submit', function (event) {
        event.preventDefault();
        var userInput = $('.search-field').val().trim();

        if (userInput.length == 0) {
            showAlert("Empty query.");
        } else if (userInput > 40) {
            showAlert("Query length > 40 characters.");
        } else if (!validateString(userInput)) {
            showAlert("Query can't contain next characters: " +
                "< > % $ & * ^ : ; ? ' \" ` \\ /.");
        } else {
            this.submit();
        }
    });

    $('#edit_form').on('submit', editAction(false));

    $('#add_form').on('submit', editAction(true));

    $('a.active').click(function (event) {
        event.preventDefault();
    });

    $('a.delete-action').click(function () {
        var href = $(this).attr('href');

        $('#deleteConfirmOk').attr('href', href);
        $('#deleteConfirmation').modal({show:true});

        return false;
    });
});

function showAlert(message) {
    $('#alert>.modal-dialog>.modal-content>.modal-body')
        .text(message);
    $('#alert').modal({show:true});
}

function validateString(string) {
    return /^[^<>%$&*^:;?'"`\\\/]+$/i.test(string);
}

function editAction(checkName) {
    return function(event) {
        event.preventDefault();
        var userInput = $('.name-field').val().trim();

        if (userInput.length == 0) {
            showAlert("Name can not be empty.");
        } else if (userInput.length > 40) {
            showAlert("Name length > 40 characters.")
        } else if (!validateString(userInput)) {
            showAlert("Name can't contain next characters: " +
                "< > % $ & * ^ : ; ? ' \" ` \\ /.");
        } else if (checkName) {
            var currentForm = this;
            $.post('find', {name: userInput}).done(function (data) {
                if (data) {
                    showAlert("Name '" + userInput +
                        "' is already exists in the database.");
                } else {
                    currentForm.submit();
                }
            }).fail(function () {
                currentForm.submit();
            });
        } else {
            this.submit();
        }
    };
}
