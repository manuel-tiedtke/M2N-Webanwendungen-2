let expandingTextareas = document.getElementsByClassName("expanding");
for (let textarea of expandingTextareas) {
    textarea.addEventListener("input", function() {
        if (this.scrollHeight > this.clientHeight) this.style.height = this.scrollHeight + 3 + "px";
    });
    textarea.style.height = textarea.offsetHeight + "px";
}
