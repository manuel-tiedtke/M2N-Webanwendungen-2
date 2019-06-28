const categoryId = new URL(location.href).searchParams.get('category')

function getCategory() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category/' + categoryId)
        request.send()
    })
}

function getFlashcards() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category/' + categoryId + '/flashcards')
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

async function loadCategory() {
    const category = await getCategory()
    document.getElementById('title').innerText = category.name
}

async function loadFlashcards() {
    const flashcards = await getFlashcards()
    for (const flashcard of flashcards) addFlashcardToHtml(flashcard, categories)
}

loadFlashcards()
loadCategory()
