
import React from "react";
import classes from "./AuthorModal.module.css";
import { RiCloseLine } from "react-icons/ri";

const AuthorModal = ({ setIsOpen, authorContent }) => {
  return (
    <div>
        <div className={classes.darkBG} onClick={() => setIsOpen(false)} />
        <div className={classes.centered}>
            <div className={classes.modal}>
            
            <div className={classes.modalHeader}>
                <h5 className={classes.heading}>Author</h5>
            </div>
          
            <button className={classes.closeBtn__cross} onClick={() => setIsOpen(false)}>
                <RiCloseLine style={{ marginBottom: "-3px" }} />
            </button>
          
            <div className={classes.modalContent}>
                <div>
                    <h1>{authorContent.name}</h1>
                    </div>
                    <div>
                        <h3>{authorContent.description}</h3>

                        <h3>Genres</h3>
                        <ul>
                            {
                                authorContent.genres.map((genre, index) => {
                                    return (<li key={index}>
                                        <h4>{genre}</h4>
                                    </li>)
                                })
                            }
                        </ul>
                    </div> 
            </div>
          
            <div className={classes.modalActions}>
                <div className={classes.actionsContainer}>
                    <button className={classes.closeBtn__block} onClick={() => setIsOpen(false)}>
                        Close
                    </button>
                </div>
            </div>

        </div>
      </div>
    </div>
  );
};

export default AuthorModal;