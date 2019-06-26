const categoryId = new URL(location.href).searchParams.get('category')

function getFlashcards() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category/' + categoryId + '/flashcards')
        request.send()
    })
}

function getCategory() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category/' + categoryId)
        request.send()
    })
}

function addFlashcardToHtml(flashcard) {
    const template = document.getElementById('flashcard-template').content
    textareas = template.querySelectorAll('textarea')
    textareas.item(0).innerText = flashcard.question
    textareas.item(1).innerText = flashcard.answer

    const flashcardHtml = document.importNode(template, true)
    const container = document.getElementById('flashcard-container')
    container.insertBefore(flashcardHtml, container.children[container.children.length - 1])
}

function loadFlashcards() {
    getFlashcards()
        .then(value => {
            for (const flashcard of value) {
                addFlashcardToHtml(flashcard)
            }
        })
        .catch(reason => console.error(reason))
}

function loadCategory() {
    getCategory()
        .then(value => {
            document.getElementById('title').innerText = value.name
        })
        .catch(reason => console.error(reason))
}

loadFlashcards()
loadCategory()
