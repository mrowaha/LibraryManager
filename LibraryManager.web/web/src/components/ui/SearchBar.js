import { useRef, useState } from 'react'

import { AiFillFilter, AiOutlineSearch } from "react-icons/ai";

import classes from './SearchBar.module.css'

function SearchBar(props) {
    const nameInputRef = useRef();
    const genreInputRef = useRef();


    const submitHandler = (e) => {
        e.preventDefault();

        //priorty given to genre
        if(genreInputRef.current.value !== "none") {
            props.setSearchByGenre(genreInputRef.current.value);
            return;
        }

        props.setSearchByName(nameInputRef.current.value);
        return;

    }

    return (
        <div className={classes.search_bar}>
            <form className={classes.search_form} onSubmit={submitHandler}>
                <div className={classes.form_control}>
                    <label htmlFor="name"  style={{ position: "relative", top : "9px" }}>Name</label>
                    <input type="text" name="name" id="name" ref={nameInputRef}/>

                    <AiFillFilter  style={{ position: "relative", top : "10px" }}/>
                    <select type="text" name="genre" id="genre" ref={genreInputRef} required>
                        <option value="none">none</option>
                        <option value="Physics">Physics</option>
                        <option value="Chemistry">Chemistry</option>
                        <option value="Mathematics">Mathematics</option>
                        <option value="Computer Science">Computer Science</option>
                    </select>
                </div>

                <div className={classes.actions}>
                    <button type="submit"><AiOutlineSearch /></button>
                </div>

            </form>
        </div>
    )
}


export default SearchBar;