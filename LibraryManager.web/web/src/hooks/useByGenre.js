import {useState, useEffect} from 'react';


function useByGenre (byGenre, context) {
    const [genre, setGenre] = useState(byGenre);

    useEffect(() => {
        let fetchUrl;
        if (genre !== "") {
            const temp = genre.replace(" ", "%20")
            fetchUrl = `http://localhost:8080/api/${context.type}?genre=${temp}`; 
            console.log(fetchUrl)

            fetch(fetchUrl)
                .then(response => {
                    return response.json();
                })
                .then(data => {
                    context.setByGenre(data);
                })
                .catch(err => {
                    console.log(err);
                })

        }
        
    }, [genre])

    return setGenre;
}


export default useByGenre;