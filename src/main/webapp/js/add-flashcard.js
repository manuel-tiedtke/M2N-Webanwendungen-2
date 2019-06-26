function createFlashcard(flashcard) {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('POST', '/api/flashcard')
        request.setRequestHeader('Content-Type', 'application/json')
        request.send(JSON.stringify(flashcard))
    })
}

function addFlashcard() {
    const flashcard = {
        question: document.getElementById('question').value,
        answer: document.getElementById('answer').value
    }

    const categories = []
    const tags = document.querySelectorAll('.tag')
    for (const tag of tags) {
        categories.push({ name: tag.innerText })
    }

    if (categories.length == 0) {
        alert('You need to specify at least one tag.')
        return
    }

   createFlashcard(flashcard)
   .then(value => {
        const request = new XMLHttpRequest()
        request.onload = event => location.href = 'dashboard.html'
        request.onerror = event => console.error(event)
        request.open('POST', '/api/flashcard/' + value + '/categories')
        request.setRequestHeader('Content-Type', 'application/json')
        request.send(JSON.stringify(categories))
   })
   .catch(reason => console.error(reason))
}

function addTag() {
    const tagInput = document.getElementById('tags')

    const template = document.getElementById('tag-template').content
    template.querySelector('.tag').innerText = tagInput.value

    const tagHtml = document.importNode(template, true)
    const container = document.getElementById('tag-container')
    container.appendChild(tagHtml)
}
