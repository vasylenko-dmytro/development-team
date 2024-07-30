// Function to manage alert message visibility
function messageAlert() {
    return {
        show: true,
        isAlertVisible() {
            return this.show === true;
        },
        hideAlert() {
            this.show = false;
        }
    };
}