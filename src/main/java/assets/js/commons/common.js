;

// define jquery plugin
(function($) {
    $.fn.extend({
        datetimePickerInstance : function() {
            return $(this).data("DateTimePicker");
        }
    });
})(jQuery);

// define validations
(function ($) {
    FormValidation.Validator.datetime = {
        validate: function (validator, $field) {
            var $source = $($field.data('datetimepicker'));
            if (!$source || !$source.val()) {
                return true;
            }
            var date = $source.datetimePickerInstance().date();
            if (!date || !date.isValid()) {
                return false;
            }
            $field.val(date.utc().format());
            return true;
        }
    };
})(jQuery);