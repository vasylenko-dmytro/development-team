// Event listener for the select avatar button click
document.querySelector('#selectAvatarButton').addEventListener('click', evt => {
    document.querySelector('#selectAvatarButton').blur();
    document.querySelector('#avatarFile').click();
});

// Event listener for the avatar image click
document.querySelector('#avatarImage').addEventListener('click', evt => {
    document.querySelector('#avatarImage').blur();
    document.querySelector('#avatarFile').click();
});

// Event listener for the avatar file input change
document.querySelector('#avatarFile').addEventListener('change', evt => {
    previewImage();
});

// Function to preview the selected avatar image
function previewImage() {
    const uploader = document.querySelector('#avatarFile');
    if (uploader.files && uploader.files[0]) {
        document.querySelector('#avatarImage').src = window.URL.createObjectURL(uploader.files[0]);
        document.querySelector('#avatarImage').classList.remove('p-6');
    } else {
        console.error('No file selected for avatar upload.');
    }
}