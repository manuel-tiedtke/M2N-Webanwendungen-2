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

function getFlashcardCategories(flashcardId) {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/flashcard/' + flashcardId + '/categories')
        request.send()
    })
}

function addFlashcardToHtml(flashcard, categories) {
    const template = document.getElementById('flashcard-template').content

    template.querySelector('.question').innerText = flashcard.question
    template.querySelector('.answer').innerText = flashcard.answer

    const flashcardHtml = document.importNode(template, true)
    const container = document.getElementById('flashcard-container')

    if (categories.length > 1) {
        const tagTemplate = document.getElementById('tag-template').content
        for (const category of categories) {
            tagTemplate.querySelector('.tag').innerText = category.name

            const tagHtml = document.importNode(tagTemplate, true)
            const tagContainer = flashcardHtml.querySelector('.tag-container')
            tagContainer.appendChild(tagHtml)
        }
    }

    container.insertBefore(flashcardHtml, container.children[container.children.length - 1])
}

async function loadCategory() {
    const category = await getCategory()
    document.getElementById('title').innerText = category.name
}

async function loadFlashcards() {
    const flashcards = await getFlashcards()
    for (const flashcard of flashcards) {
        const categories = await getFlashcardCategories(flashcard.id)
        addFlashcardToHtml(flashcard, categories)
    }
}

loadFlashcards()
loadCategory()
