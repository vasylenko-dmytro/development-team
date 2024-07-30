function sidebarMenu() {
    return {
        show: false,
        openSidebar() {
            this.show = true;
        },
        closeSidebar() {
            this.show = false;
        },
        isVisible() {
            return this.show === true;
        }
    };
}
