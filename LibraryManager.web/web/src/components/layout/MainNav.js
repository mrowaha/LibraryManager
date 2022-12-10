import { useContext } from 'react';
import {Link} from 'react-router-dom'
import classes from './MainNav.module.css'

function MainNav() {
    

    return (
        <header className={classes.header}>
            <div className={classes.logo}>Library Manager</div>
            <nav>
                <ul>
                    <li>
                        <Link to='/books'>All Books</Link>
                    </li>
                    <li>
                        <Link to='/authors'>All Authors</Link>
                    </li>
                 
                </ul>
            </nav>
        </header>
    )
}

export default MainNav;