import { createContext, useState, useEffect } from "react";

//sets the initial values
const AuthorsContext = createContext({
    type : 'author',
    authors : [],
    loading : true,
    setByName : (byNameArr) => {},
    setByGenre : (byGenreArr) => {}
});

export function AuthorsContextProvider(props) {
    const [authors, setAuthors] = useState([]);
    const [loading, setIsLoading] = useState(true);

    useEffect(()=> {
        setIsLoading(true);
        fetch('http://localhost:8080/api/author')
            .then(response => {
                return response.json();
            })
            .then(data => {
                setIsLoading(false);
                setAuthors(data);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    const setByName = (byNameArr) => {
        setAuthors(byNameArr);
    }

    const setByGenre = (byGenreArr) => {
        setAuthors(byGenreArr);
    }
    
    const context = {
        type : 'author',
        authors : authors,
        loading : loading,
        setByName : setByName,
        setByGenre : setByGenre
    }; 
    
    return (
        <AuthorsContext.Provider value={context}> 
            {props.children}
        </AuthorsContext.Provider>
    )
}

export default AuthorsContext;