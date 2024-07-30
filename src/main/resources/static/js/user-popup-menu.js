// Function to manage the visibility of the user popup menu
function userPopupMenu() {
    return {
        show: false,
        toggleVisibility() {
            this.show = !this.show;
        },
        close() {
            this.show = false;
        },
        isVisible() {
            return this.show === true;
        }
    };
}
