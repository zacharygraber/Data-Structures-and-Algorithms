module L9 where

open import Data.Nat
open import Data.Nat.Properties
open import Function
open import Relation.Binary.PropositionalEquality
open ≡-Reasoning

-- 

data Tree (A : Set) : Set where
  Leaf : A → Tree A
  Node : Tree A → A → Tree A → Tree A

t1 t2 t3 t4 t5 : Tree ℕ
t1 = Leaf 5
t2 = Leaf 7
t3 = Node t1 8 t2
t4 = Node t3 2 t3
t5 = Node t4 1 (Leaf 4)

nodes : {A : Set} → Tree A → ℕ
nodes (Leaf _) = 1
nodes (Node left _ right) = 1 + (nodes left + nodes right)

iNodes : {A : Set} → Tree A → ℕ
iNodes (Leaf _) = 0
iNodes (Node left _ right) = 1 + (iNodes left + iNodes right)

leaves : {A : Set} → Tree A → ℕ
leaves (Leaf _) = 1
leaves (Node left _ right) = leaves left + leaves right

sumData : Tree ℕ → ℕ
sumData (Leaf n) = n
sumData (Node left n right) = n + (sumData left + sumData right)

mapTree : {A B : Set} → (A → B) → Tree A → Tree B
mapTree f (Leaf n) = Leaf (f n)
mapTree f (Node t₁ n t₂) = Node (mapTree f t₁) (f n) (mapTree f t₂)

---

2*≡ : (b c : ℕ) → 2 * (b + c) ≡ 2 * b + 2 * c
2*≡ 0 c = refl
2*≡ (suc b) c =
  2 * (suc (b + c))
    ≡⟨ *-suc 2 (b + c) ⟩
  2 + 2 * (b + c)
    ≡⟨ cong (suc ∘ suc) (2*≡ b c) ⟩
  2 + (2 * b + 2 * c)
    ≡⟨ sym (+-assoc 2 (2 * b) (2 * c)) ⟩
  (2 + 2 * b) + 2 * c
    ≡⟨ cong (λ h → h + 2 * c) (sym (*-suc 2 b)) ⟩ 
  2 * suc b + 2 * c ∎

+-group1 : (a b : ℕ) → (1 + a) + (1 + b) ≡ 2 + (a + b)
+-group1 a b = 
  (1 + a) + (1 + b)
    ≡⟨ sym (+-assoc (1 + a) 1 b)  ⟩
  ((1 + a) + 1) + b
    ≡⟨ cong (λ h → h + b) (+-comm (1 + a) 1) ⟩ 
  2 + (a + b) ∎

+-group2 : (a b : ℕ) →
           (1 + ((1 + 2 * a) + (1 + 2 * b))) ≡
           (1 + (2 * (1 + a + b)))
+-group2 a b = 
  1 + ((1 + 2 * a) + (1 + 2 * b))
    ≡⟨ cong suc (sym (+-assoc (1 + 2 * a) 1 (2 * b))) ⟩
  1 + (((1 + 2 * a) + 1) + 2 * b)
    ≡⟨ cong (λ h → 1 + (h + 2 * b)) (+-comm (1 + 2 * a) 1) ⟩
  1 + ((2 + 2 * a) + 2 * b)
    ≡⟨ cong (λ h → 1 + (h + 2 * b)) (sym (2*≡ 1 (a))) ⟩
  1 + (2 * (1 + a) + 2 * b) 
    ≡⟨ cong suc (sym (2*≡ (1 + a) (b))) ⟩
  1 + (2 * (1 + a + b)) ∎

---

thm1 : {A : Set} → (tree : Tree A) →
       sumData (mapTree (λ _ → 1) tree) ≡ nodes tree
thm1 (Leaf n) = refl
thm1 (Node left n right) = ⟨ { !! } ⟩

thm2 : {A : Set} → (tree : Tree A) →
       leaves tree ≡ 1 + iNodes tree
thm2 (Leaf n) = refl
thm2 (Node left n right) = ⟨ { !! } ⟩

thm3 : {A : Set} → (tree : Tree A) →
       nodes tree ≡ 1 + 2 * iNodes tree
thm3 (Leaf n) = refl
thm3 (Node left n right) = ⟨ { !! } ⟩

