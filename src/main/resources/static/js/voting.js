function vote(movieId, voteType) {
    console.log(`Vote function called: movieId=${movieId}, voteType=${voteType}`);

    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/api/votes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({ movieId: movieId, voteType: voteType })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Response data:', data);
            if (data.success) {
                updateVoteCounts(movieId, data.likeCount, data.hateCount);
            } else {
                console.error('Error:', data.message);
                alert(data.message || 'An error occurred while processing your vote.');
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            alert('An error occurred while processing your vote. Please try again.');
        });
}

function updateVoteCounts(movieId, likeCount, hateCount) {
    const movieCard = document.querySelector(`.card[data-movie-id="${movieId}"]`);
    if (movieCard) {
        const likeButton = movieCard.querySelector('.btn-outline-success .badge');
        const hateButton = movieCard.querySelector('.btn-outline-danger .badge');

        if (likeButton) likeButton.textContent = likeCount;
        if (hateButton) hateButton.textContent = hateCount;

        console.log(`Updated UI: likeCount=${likeCount}, hateCount=${hateCount}`);

        updateButtonStyles(movieCard, likeCount, hateCount);
    } else {
        console.error(`Movie card with id ${movieId} not found`);
    }
}

function updateButtonStyles(movieCard, likeCount, hateCount) {
    const likeButton = movieCard.querySelector('.btn-outline-success');
    const hateButton = movieCard.querySelector('.btn-outline-danger');

    if (likeButton) {
        likeButton.classList.toggle('active', likeCount > 0);
        likeButton.disabled = false;
    }
    if (hateButton) {
        hateButton.classList.toggle('active', hateCount > 0);
        hateButton.disabled = false;
    }
}