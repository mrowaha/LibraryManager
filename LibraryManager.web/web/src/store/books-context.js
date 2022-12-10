import { createContext, useState, useEffect } from "react";

//sets the initial values
const BooksContext = createContext({
    type : 'book',
    books : [],
    loading : true,
    setByName : (byNameArr) => {},
    setByGenre : (byGenreArr) => {}
});

export function BooksContextProvider(props) {
    const [books, setBooks] = useState([]);
    const [loading, setIsLoading] = useState(true);

    useEffect(()=> {
        setIsLoading(true);
        fetch('http://localhost:8080/api/book')
        .then(response => {
            return response.json();
        })
        .then(data => {
            setIsLoading(false);
            setBooks(data);
        })
        .catch(err => {
            console.log(err);
        })
    }, []);


    const setByName = (byNameArr) => {
        setBooks(byNameArr);
    }

    const setByGenre = (byGenreArr) => {
        setBooks(byGenreArr);
    }
    
    const context = {
        type : 'book',
        books : books,
        loading : loading,
        setByName : setByName,
        setByGenre : setByGenre
    }; 
    
    return (
        <BooksContext.Provider value={context}> 
            {props.children}
        </BooksContext.Provider>
    )
}

export default BooksContext