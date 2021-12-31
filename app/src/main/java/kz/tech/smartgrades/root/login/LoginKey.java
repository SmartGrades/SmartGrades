package kz.tech.smartgrades.root.login;

import java.io.Serializable;

public enum LoginKey implements Serializable {
    CODE {
        @Override
        public String toString() {
            return "Code";
        }
    },

    AVATAR {
        @Override
        public String toString() {
            return "Avatar";
        }
    },

    BIRTHDAY {
        @Override
        public String toString() {
            return "Birthday";
        }
    },

    FIRST_NAME {
        @Override
        public String toString() {
            return "FirstName";
        }
    },

    LAST_NAME {
        @Override
        public String toString() {
            return "LastName";
        }
    },

    ID {
        @Override
        public String toString() {
            return "UserId";
        }
    },

    LOGIN {
        @Override
        public String toString() {
            return "Login";
        }
    },

    PHONE {
        @Override
        public String toString() {
            return "Phone";
        }
    },

    MAIL {
        @Override
        public String toString() {
            return "Mail";
        }
    },

    PHONE_OR_MAIL {
        @Override
        public String toString() {
            return "PhoneOrMail";
        }
    },

    ABOUT_ME {
        @Override
        public String toString() {
            return "AboutMe";
        }
    },

    DESCRIPTION {
        @Override
        public String toString () {
            return "KeyWords";
        }
    },

    NAME {
        @Override
        public String toString () {
            return "Name";
        }
    },

    ADDRESS {
        @Override
        public String toString () {
            return "Address";
        }
    },

    SITE {
        @Override
        public String toString () {
            return "Site";
        }
    },

    PASSWORD {
        @Override
        public String toString () {
            return "Password";
        }
    },

    TOKEN {
        @Override
        public String toString () {
            return "Token";
        }
    },

    TYPE {
        @Override
        public String toString () {
            return "Type";
        }
    };
}
