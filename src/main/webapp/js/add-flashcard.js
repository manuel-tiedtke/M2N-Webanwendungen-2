function addFlashcard() {
    const flashcard = {
        question: document.getElementById('question').value,
        answer: document.getElementById('answer').value
    }

    const request = new XMLHttpRequest()
    request.onload = event => location.href = 'dashboard.html'
    request.onerror = event => console.error(event)
    request.open('POST', '/api/flashcard')
    request.setRequestHeader('Content-Type', 'application/json')
    request.send(JSON.stringify(flashcard))
}
