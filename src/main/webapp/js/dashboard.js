function getCategories() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category')
        request.send()
    })
}

function addCategoryToHtml(category) {
    const template = document.getElementById('category-template').content
    template.querySelector('h5').innerText = category.name

    const categoryHtml = document.importNode(template, true)
    const container = document.getElementById('category-container')
    container.insertBefore(categoryHtml, container.children[container.children.length - 1])
}

function loadCategories() {
    getCategories()
        .then(value => {
            for (const category of value) addCategoryToHtml(category)
        })
        .catch(reason => console.error(reason))
}

loadCategories()