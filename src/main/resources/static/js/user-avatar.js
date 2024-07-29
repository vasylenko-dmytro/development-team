document.querySelector('#selectAvatarButton').addEventListener('click', evt => {
    document.querySelector('#selectAvatarButton').blur();
    document.querySelector('#avatarFile').click();
});

document.querySelector('#avatarImage').addEventListener('click', evt => {
    document.querySelector('#avatarImage').blur();
    document.querySelector('#avatarFile').click();
});


document.querySelector('#avatarFile').addEventListener('change', evt => {
    previewImage();
});

function previewImage() {
    var uploader = document.querySelector('#avatarFile');
    if (uploader.files && uploader.files[0]) {
        document.querySelector('#avatarImage').src = window.URL.createObjectURL(uploader.files[0]);
        document.querySelector('#avatarImage').classList.remove('p-6');
    }
}