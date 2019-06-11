const expandingTextareas = document.getElementsByClassName('expanding')
for (const textarea of expandingTextareas) {
    textarea.addEventListener('input', () => {
        if (this.scrollHeight > this.clientHeight) this.style.height = this.scrollHeight + 3 + 'px'
    })
    textarea.style.height = textarea.offsetHeight + 'px'
}
