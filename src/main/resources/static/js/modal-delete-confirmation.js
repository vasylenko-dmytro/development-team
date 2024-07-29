function modalDeleteConfirmation() {
    return {
        show: false,
        name: '',
        deleteUrl: '',
        hideModal() {
            this.show = false;
        },
        isVisible() {
            return this.show === true;
        },
        getName() {
            return this.name;
        },
        getDeleteUrl() {
            return this.deleteUrl;
        },
        showModal($event) {
            this.name = $event.detail.name;
            this.deleteUrl = $event.detail.deleteUrl;
            this.show = true;
        }
    };
}