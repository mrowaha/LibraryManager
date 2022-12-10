import { useState, useEffect} from "react";
import {useParams } from "react-router-dom";

import classes from '../components/authors/AuthorItem.module.css'

import Card from '../components/ui/Card'

function AuthorById() {


    const {id} = useParams();
    const [author, setAuthor] = useState({});
    const [books, setBooks] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/api/author/${id}`)
            .then(response => {
                return response.json();
            })
            .then(authorData => {
                console.log(authorData);
                setAuthor(authorData);
                return fetch(`http://localhost:8080/api/author/${id}/book`)
            })
            .then(response =>{
                return response.json()
            })
            .then(booksData => {
                setBooks(booksData);
            })
            .catch(err => {
                console.log(err);
            })
    }, [id])

    return (
        <li className={classes.item}>
            <Card>
                <div className={classes.heading}>
                    <h1>{author.name}</h1>
                </div>
                <div className={classes.content}>
                    <h3>{author.description}</h3>
                    <h3>Genres Writting For</h3>
                    <ul>
                        {
                            author.genres?.map((genre, index) => {
                                return (<li key={index}>
                                    <h4>{genre}</h4>
                                </li>)
                            })
                        }
                    </ul>
                </div>

            <h2>Books</h2>
            { 
                books?.map((book, index) => {
                    return (
                        <div className={classes.author_books}>
                            <h3>Name : {book.name}</h3>
                        </div>
                    )
                })
            }

            </Card>
        </li>
    )

}

export default AuthorById;