import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    state: () => ({
        id: null as number | null,
        username: '',
        isAdmin: false
    }),

    actions: {
        setUser(id: number, username: string, isAdmin: boolean) {
            this.id = id
            this.username = username
            this.isAdmin = isAdmin
        }
    }
})