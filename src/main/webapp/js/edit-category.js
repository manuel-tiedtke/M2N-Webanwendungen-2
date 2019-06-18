const categoryId = new URL(location.href).searchParams.get('category')

function editCategory() {
    const category = {
        name: document.getElementById('name').value,
        tagline: document.getElementById('tagline').value,
        description: document.getElementById('description').value
    }

    const request = new XMLHttpRequest()
    request.onload = event => location.href = 'dashboard.html'
    request.onerror = event => console.error(event)
    request.open('PUT', '/api/category/' + categoryId)
    request.setRequestHeader('Content-Type', 'application/json')
    request.send(JSON.stringify(category))
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

function loadCategory() {
    getCategory()
        .then(value => {
            document.getElementById('name').value = value.name
            document.getElementById('tagline').value = value.tagline ? value.tagline : ''
            document.getElementById('description').value = value.description ? value.description : ''
        })
        .catch(reason => console.error(reason))
}

loadCategory()
