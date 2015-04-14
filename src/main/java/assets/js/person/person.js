;
$(function () {
    (function ($birthday, $form) {
        $('input.alv-datepicker').on("dp.change", function () {
            var date = $(this).datetimePickerInstance().date();
            if (date && date.isValid()) {
                $birthday.val(date.utc().format());
            } else {
                $birthday.val('');
            }
            $form.data('formValidation')
                .revalidateField('birthday');
        }).datetimepicker(defaultDatePickerOptions)
            .datetimePickerInstance()
            .date(moment.utc($birthday.val()));
    })($('input[name="birthday"]'), $('#form-main'));
});