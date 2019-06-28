const categoryId = new URL(location.href).searchParams.get('category')
let flashcard

function checkAnswer() {
    document.getElementById('answer').disabled = true
    document.getElementById('check-answer').disabled = true
    document.getElementById('correct-answer-group').classList.remove('d-none')
}

function submitRating(rating) {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve()
        request.onerror = event => reject(event)
        request.open('POST', '/api/flashcard/' + flashcard.id + '/answer')
        request.setRequestHeader('Content-Type', 'application/json')
        request.send(JSON.stringify(rating))
    })
}

async function rateAnswer(rating) {
    await submitRating(rating)

    const answer = document.getElementById('answer')
    answer.value = ''
    answer.disabled = false
    answer.focus()

    document.getElementById('check-answer').disabled = false
    document.getElementById('correct-answer-group').classList.add('d-none')

    loadNextDueFlashcard()
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

function getNextDueFlashcard() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category/' + categoryId + '/flashcards?onlyDue=true&limit=1')
        request.send()
    })
}

function getFlashcardCategories() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/flashcard/' + flashcard.id + '/categories')
        request.send()
    })
}

async function loadCategory() {
    const category = await getCategory()
    document.getElementById('title').innerText = category.name
}

async function loadNextDueFlashcard() {
    const flashcards = await getNextDueFlashcard()
    if (flashcards.length == 0) {
        alert('Congratulations! You finished training for today!')
        location.href = 'dashboard.html'
    }

    flashcard = flashcards[0]
    const categories = await getFlashcardCategories()

    document.getElementById('question').value = flashcard.question
    document.getElementById('correct-answer').value = flashcard.answer

    const tagContainer = document.getElementById('tag-container')
    while (tagContainer.lastChild) child.remove()
    if (categories.length > 1) {
        const tagTemplate = document.getElementById('tag-template').content
        for (const category of categories) {
            tagTemplate.querySelector('.tag').innerText = category.name

            const tagHtml = document.importNode(tagTemplate, true)

            tagContainer.appendChild(tagHtml)
        }
    }
}

loadCategory()
loadNextDueFlashcard()
